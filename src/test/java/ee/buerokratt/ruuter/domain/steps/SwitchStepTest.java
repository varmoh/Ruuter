package ee.buerokratt.ruuter.domain.steps;


import ee.buerokratt.ruuter.BaseTest;
import ee.buerokratt.ruuter.domain.ConfigurationInstance;
import ee.buerokratt.ruuter.domain.steps.conditional.Condition;
import ee.buerokratt.ruuter.domain.steps.conditional.SwitchStep;
import ee.buerokratt.ruuter.helper.MappingHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SwitchStepTest extends BaseTest {
    @Mock
    private MappingHelper mappingHelper;

    @Test
    void execute_shouldJumpToCorrectStep() {
        ConfigurationInstance instance = new ConfigurationInstance(scriptingHelper, applicationProperties, new HashMap<>(), new HashMap<>(), new HashMap<>(), mappingHelper, "", tracer);
        Condition firstCondition = new Condition() {{
            setNextStepName("second_step");
            setConditionStatement("${currentTime == \"Friday\"}");
        }};
        Condition secondCondition = new Condition() {{
            setNextStepName("third_step");
            setConditionStatement("${currentTime == \"Saturday\"}");
        }};
        Condition thirdCondition = new Condition() {{
            setNextStepName("fourth_step");
            setConditionStatement("${currentTime == \"Sunday\"}");
        }};
        List<Condition> conditions = List.of(firstCondition, secondCondition, thirdCondition);
        SwitchStep switchStep = new SwitchStep() {{
            setConditions(conditions);
            setNextStepName("fifth_step");
            setName("switch");
        }};

        instance.getContext().put("currentTime", "Sunday");
        when(scriptingHelper.containsScript(anyString())).thenReturn(true);
        when(scriptingHelper.evaluateScripts("${currentTime == \"Sunday\"}", instance.getContext())).thenReturn(true);
        switchStep.execute(instance);

        assertEquals("fourth_step", switchStep.getNextStepName());
    }
}