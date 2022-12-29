package maestrogroup.core.mapping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamAndImportantRes {
    private int teamIdx;
    private String teamName;
    private String teamImgUrl;
    private int count;
    private int important;
}