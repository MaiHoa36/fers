package fpt.edu.eresourcessystem.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionLoadMoreResponse {
    private List<QuestionResponseDto> questions;
    private boolean checkHasMore;

}
