package s03.view.CustomInterface;

import s03.CustomException.ActionControlException;

public interface ActionControlPanel {
    public void add() throws ActionControlException;
    public void remove();
}
