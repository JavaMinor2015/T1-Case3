package peaseloxes.toolbox.util.testUtil;

import rest.service.RestService;

/**
 * Created by alex on 1/11/16.
 */
public class TestService extends RestService<TestObject> {
    @Override
    public void initRepository() {

    }

    @Override
    public Class<? extends RestService<TestObject>> getClazz() {
        return TestService.class;
    }
}
