package fpt.edu.eresourcessystem.dto.Response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataTablesResponse<T> {

    private List<T> data;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;

    // Các getters và setters
}

