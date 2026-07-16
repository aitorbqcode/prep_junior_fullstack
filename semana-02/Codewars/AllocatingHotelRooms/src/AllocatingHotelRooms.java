import java.util.Arrays;
import java.util.PriorityQueue;

public class AllocatingHotelRooms {
    public static int[] allocateRooms(int[][] customers) {

        int n = customers.length;
        int[] arrayRoom = new int[n];

        int[][] sortedCustomers = new int[n][3];
        for (int i = 0; i < n; i++) {
            sortedCustomers[i][0] = customers[i][0]; // llegada
            sortedCustomers[i][1] = customers[i][1]; // salida
            sortedCustomers[i][2] = i;                // índice original
        }

        Arrays.sort(sortedCustomers, (a, b) -> Integer.compare(a[0], b[0]));

        PriorityQueue<int[]> activeRooms = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        int roomCounter = 0;

        for (int[] customer : sortedCustomers) {
            int arrival = customer[0];
            int departure = customer[1];
            int originalIndex = customer[2];

            if (!activeRooms.isEmpty() && activeRooms.peek()[0] < arrival) {

                int[] reusableRoom = activeRooms.poll();
                arrayRoom[originalIndex] = reusableRoom[1];

                reusableRoom[0] = departure;
                activeRooms.add(reusableRoom);
            } else {
                roomCounter++;
                arrayRoom[originalIndex] = roomCounter;
                activeRooms.add(new int[]{departure, roomCounter});
            }
        }

        return arrayRoom;
    }

    public static void main(String[] args) {
        int[][] testCases = {{15, 22}, {2, 4}, {6, 9}, {3, 33}, {12, 21}};
        System.out.println(Arrays.toString(allocateRooms(testCases)));
    }
}


/*
Task

Allocate customers to hotel rooms based on their arrival and departure days. Each customer wants their own room, so two customers can stay in the same room only if the departure day of the first customer is earlier than the arrival day of the second customer. The number of rooms used should be minimized.
Input

A list or array of n customers, 1 ≤ n ≤ 1000. Each customer is represented by (arrival_day, departure_day), which are positive integers satisfying arrival_day ≤ departure_day.
Output

A list or array of size n, where element i indicates the room that customer i was allocated. Rooms are numbered 1,2, ..., k for some 1 ≤ k ≤ n. Any allocation that minimizes the number of rooms k is a valid solution.
Example:

Suppose customers is [(1,5), (2,4), (6,8), (7,7)].
Clearly customers 1 and 2 cannot get the same room. Customer 3 can use the same room as either of them, because they both leave before customer 3 arrives. Then customer 4 can be given the other room.
So any of [1,2,1,2], [1,2,2,1], [2,1,2,1], [2,1,1,2] is a valid solution.

NOTE: The list of customers is not necessarily ordered by arrival_time.
Source: CSES
Related kata:

Minimum number of taxis is a harder version, because the lists are bigger.

The Hotel with Infinite Rooms
Is room reserved?
 */