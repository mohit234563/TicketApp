package com.MovieTicketBooking.App.Catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name="seats")
@Getter@Setter@NoArgsConstructor
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String label;
    private String section;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "layout_id",nullable = false)
    private Layouts layout;
    @Column(name = "row_no")
    private int rowNo;
    @Column(name = "col_no")
    private int colNo;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Object> attributes;
}
