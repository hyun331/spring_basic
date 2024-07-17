package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//생성시간, 수정시간은 모든 entity가 가지는 컬럼

@Getter
//기본적으로 entity는 상속관계가 불가능하여 해당 어노테이션을 붙여야 상속관계 성립 가능
@MappedSuperclass
public abstract class BaseEntity {
    //이런 캐멀케이스 변수는 DB에 _(언더바)로 들어감 -> created_time 으로 들어감.
    @CreationTimestamp  //DB에는 current_timestamp가 적용되지 않음
    private LocalDateTime createdTime;

    @UpdateTimestamp    //데이터 업데이트 될 때마다 갱신
    private LocalDateTime updateTime;
}
