package com.csbaic.jd.service.user.active;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 活跃度计算请求
 */
public class ActiveScoreRequest {

    /**
     * 计算用户的id
     */
    private final Long userId;

    /**
     * 开始时间
     */
    private final LocalDateTime start;


    /**
     * 结束时间
     */
    private final LocalDateTime end;

    public ActiveScoreRequest(Long userId, LocalDateTime start, LocalDateTime end) {
        this.userId = userId;
        this.start = start;
        this.end = end;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }



    public static ActiveScoreRequest between(Long userId, int days){
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return new ActiveScoreRequest(userId, start, end);
    }



    public static ActiveScoreRequest today(Long userId){
        return between(userId, 0);
    }


    public static ActiveScoreRequest total(Long userId){
        return between(userId, 30);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveScoreRequest that = (ActiveScoreRequest) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, start, end);
    }
}
