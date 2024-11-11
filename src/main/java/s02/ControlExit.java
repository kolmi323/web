package s02;

import java.math.BigDecimal;

public class ControlExit {
    public boolean isExit(String str) {
        return str.equalsIgnoreCase("exit");
    }

    public boolean isExit(BigDecimal bd) {
        return bd.equals(BigDecimal.ZERO);
    }
}
