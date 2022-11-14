package maestrogroup.core.mapping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mapping {
    int mappingIdx;
    int important;
    int teamIdx;
    int userIdx;
}
