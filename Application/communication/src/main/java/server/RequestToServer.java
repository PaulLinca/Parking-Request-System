package server;

import java.util.HashMap;
import java.util.Map;

public class RequestToServer
{
    private Map<String, String> params = new HashMap<>();

    public Map<String, String> getParams()
    {
        return params;
    }

    public void setParams(Map<String, String> params)
    {
        this.params = params;
    }
}
