package performancebased;

import lombok.AllArgsConstructor;
import lombok.Data;
import machine.Color;
import machine.PieceEnum;

@Data
@AllArgsConstructor
public class Piece {
    PieceEnum kind;
    Color color;
}
