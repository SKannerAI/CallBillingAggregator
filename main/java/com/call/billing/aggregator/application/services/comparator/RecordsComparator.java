package com.call.billing.aggregator.application.services.comparator;

import com.call.billing.aggregator.application.rest.model.CallRecord;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecordsComparator {
    /*
    Returns -ve number if
     */
    private static void sortRecordsByTimeStampAsc(List<CallRecord> callRecords) {
        Collections.sort(callRecords, new Comparator<CallRecord>() {

            @Override
            public int compare(CallRecord c1, CallRecord c2) {

                // Convert epoch timestamps to Instant objects
                Instant instant1 = Instant.ofEpochSecond(c1.getStartTimestamp());
                Instant instant2 = Instant.ofEpochSecond(c2.getStartTimestamp());

                return instant1.compareTo(instant2);
            }
        });
    }
}
