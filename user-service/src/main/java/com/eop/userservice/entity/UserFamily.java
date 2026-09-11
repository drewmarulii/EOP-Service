package com.eop.userservice.entity;

import com.eop.baseservice.entity.MasterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_families")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user_families SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND version = ?")
@NoArgsConstructor
@AllArgsConstructor
public class UserFamily extends MasterEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id", referencedColumnName = "id", nullable = false)
    private User leaderUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_user_id", referencedColumnName = "id", nullable = false)
    private User memberUserId;

}
