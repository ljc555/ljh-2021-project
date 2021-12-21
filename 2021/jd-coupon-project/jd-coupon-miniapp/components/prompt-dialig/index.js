// components/prompt-dialig/index.js
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        cancelText: {
            type: String,
            default: "取消"
        },
        confirmText: {
            type: String,
            default: "确定"
        },
        promptMsg: {
            type: String,
            default: "提示消息",
        },
        invitationDialog: {
            type: Boolean,
            default: false
        },
        isShowCancel: {
            type: Boolean,
            default: false
        }

    },

    /**
     * 组件的初始数据
     */
    data: {},

    /**
     * 组件的方法列表
     */
    methods: {
        hideModal(event) {
            this.setData({invitationDialog:false})
          if(event.currentTarget.dataset.iscode == 0)
            this.triggerEvent("onConfirm")
        }
    }
})
