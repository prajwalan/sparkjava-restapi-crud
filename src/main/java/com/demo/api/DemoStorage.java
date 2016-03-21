package com.demo.api;

import java.util.HashMap;
import java.util.Map;

import com.demo.db.model.Customer;

/**
 * Focus of this project is demonstration of Sparkjava REST API. Hence this temporary storage is created so as to skip the
 * database communication code. This code should never be used in any production system.
 * 
 */
public class DemoStorage {

    public static Map<Integer, Customer> customerMap = new HashMap<>();
    public static int nextCustomerId;

    static {
        customerMap.put(1, new Customer(1, "Anna", "Bedecs", "123 1st Street", "Seattle", "USA", "anna.bedecs@gmail.com",
                "(123)555-0100", 99999));
        customerMap.put(2, new Customer(2, "Antonio", "Gratacos Solsona", "123 2nd Street", "Boston", "USA",
                "antonio.g.solsona@gmail.com", "(123)555-0200", 99998));

        nextCustomerId = 3;
    }
}
