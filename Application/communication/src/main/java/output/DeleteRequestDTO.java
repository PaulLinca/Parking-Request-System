package output;

import lombok.Data;


@Data
public class DeleteRequestDTO implements DTO
{
    private final long id;
    private String status;
    private String message;
}
