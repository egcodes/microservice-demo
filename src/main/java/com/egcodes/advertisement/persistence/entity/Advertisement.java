package com.egcodes.advertisement.persistence.entity;

import com.egcodes.advertisement.enums.Category;
import com.egcodes.advertisement.enums.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String title;

    @Column(length = 200)
    @NotNull
    private String detail;

    @NotNull
    private Category category;

    @NotNull
    private Status status;

    @NotNull
    private OffsetDateTime createDate;

    private OffsetDateTime endDate;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<StatusHistory> statusHistory;

}
