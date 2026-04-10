import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class TrainConsistManagementAppTest {

    @Test
    public void testValidBogieCapacity() throws Exception {
        Bogie b = new Bogie("Sleeper", 72);
        assertEquals(72, b.capacity);
    }

    @Test(expected = InvalidCapacityException.class)
    public void testInvalidBogieCapacity() throws Exception {
        new Bogie("AC Chair", 0);
    }

    @Test
    public void testLinearSearchFound() {
        String[] ids = {"BG101", "BG205", "BG309", "BG412", "BG550"};
        String key = "BG309";

        boolean found = false;

        for (String id : ids) {
            if (id.equals(key)) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }

    @Test
    public void testBinarySearchFound() {
        String[] ids = {"BG101", "BG205", "BG309", "BG412", "BG550"};
        Arrays.sort(ids);

        String key = "BG412";

        int low = 0, high = ids.length - 1;
        boolean found = false;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = ids[mid].compareTo(key);

            if (cmp == 0) {
                found = true;
                break;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        assertTrue(found);
    }

    @Test
    public void testUC20EmptyTrainCheck() {
        LinkedList<String> trainConsist = new LinkedList<>();

        try {
            if (trainConsist.isEmpty()) {
                throw new IllegalStateException("Cannot search: No bogies available in the train!");
            }
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            assertEquals("Cannot search: No bogies available in the train!", e.getMessage());
        }
    }
}