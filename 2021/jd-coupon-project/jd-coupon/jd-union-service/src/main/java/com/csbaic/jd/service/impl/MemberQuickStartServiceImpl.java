package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.MemberQuickStart;
import com.csbaic.jd.dto.SimpleMemberUserInfo;
import com.csbaic.jd.entity.MemberQuickStartEntity;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.mapper.MemberQuickStartMapper;
import com.csbaic.jd.service.IMemberQuickStartService;
import com.csbaic.jd.service.IWechatUserService;
import com.csbaic.jd.service.user.MemberService;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Nullable;

/**
 * <p>
 * 新人上手 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-04-14
 */
@Service
public class MemberQuickStartServiceImpl extends ServiceImpl<MemberQuickStartMapper, MemberQuickStartEntity> implements IMemberQuickStartService {

    @Autowired
    private IWechatUserService wechatUserService;


    @Autowired
    private MemberService memberService;


    @Override
    public void save(MemberQuickStart quickStart) {
        if(quickStart == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "请求参数错误");
        }

        if(Strings.isNullOrEmpty(quickStart.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "标题不能为空");
        }

        if(quickStart.getStyle() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "显示样式不能为空");
        }


        if(quickStart.getType() == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "操作类型不能为空");
        }


        //html转义
        if(!Strings.isNullOrEmpty(quickStart.getContent())){
            String richText = HtmlUtils.htmlEscape(quickStart.getContent());
            quickStart.setContent(richText);
        }


        MemberQuickStartEntity entity = ObjectConvert.spring(quickStart , MemberQuickStartEntity.class);
        save(entity);
    }

    @Override
    public IPage<MemberQuickStart> getQuickStart(int pageIndex, int pageSize) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;

        if(authentication != null && authentication.getPrincipal() instanceof UserEntity ){
            user = (UserEntity) authentication.getPrincipal();
        }





        SimpleMemberUserInfo teacher = user != null ? memberService.getFirstTeacherOf(user.getId()) : null;
        SimpleMemberUserInfo memberUserInfo = user != null ? memberService.getMemberInfoOfUser(user.getId()) : null;

        final EvaluationContext context = new StandardEvaluationContext();  // 表达式的上下文,
        context.setVariable("user", memberUserInfo);
        context.setVariable("teacher", teacher);

        return page(new Page<>(pageIndex, pageSize),
                Wrappers.<MemberQuickStartEntity>query().orderByAsc(MemberQuickStartEntity.SORT)
        )
                .convert(new Function<MemberQuickStartEntity, MemberQuickStart>() {
                    @Nullable
                    @Override
                    public MemberQuickStart apply(@Nullable MemberQuickStartEntity input) {
                        return convert(context, input);
                    }
                });
    }



    private  MemberQuickStart convert(EvaluationContext context, MemberQuickStartEntity entity){
        MemberQuickStart quickStart  = ObjectConvert.spring(entity, MemberQuickStart.class);
        String content = quickStart.getContent();

        ExpressionParser parser = new SpelExpressionParser();

        if (!Strings.isNullOrEmpty(content)) {
            content = parser.parseExpression(content, new TemplateParserContext()).getValue(context, String.class);
            content = HtmlUtils.htmlUnescape(content);
            quickStart.setContent(content);
        }

        if(!Strings.isNullOrEmpty(quickStart.getData())){
            String data  = parser.parseExpression(quickStart.getData(), new TemplateParserContext()).getValue(context, String.class);
            quickStart.setData(data);
        }

        return quickStart;
    }
}
