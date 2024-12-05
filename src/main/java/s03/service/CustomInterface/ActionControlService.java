package s03.service.CustomInterface;

public interface ActionControlService <M> {
    public boolean create(M model);
    public boolean delete(M model);
}
