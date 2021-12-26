package com.jeethink.process.general.service;

import java.util.List;

import com.jeethink.process.general.domain.HistoricActivity;

public interface IProcessService {

    /**
     * 查询审批历史列表
     * @param processInstanceId
     * @param historicActivity
     * @return
     */
    List<HistoricActivity> selectHistoryList(String processInstanceId, HistoricActivity historicActivity);

}
