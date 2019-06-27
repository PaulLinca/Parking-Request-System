package output;

import lombok.Data;

import java.util.List;

@Data
public class RequestListDTO implements DTO
{
    private final List<RequestDTO> list;
}
