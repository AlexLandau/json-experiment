import net.alloyggp.weaver.api.WeaverContext;
import net.alloyggp.weaver.impl.jackson.JacksonWeaverContext;
import net.alloyggp.weaver.test.RoundTripTest;

public class JacksonRoundTripTest extends RoundTripTest {
    @Override
    protected WeaverContext<?> getContext() {
        return JacksonWeaverContext.INSTANCE;
    }
}
