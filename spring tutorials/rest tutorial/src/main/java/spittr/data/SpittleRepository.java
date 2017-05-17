package spittr.data;

import spittr.Spittle;

import java.util.List;

/**
 * Created by grace on 15/02/17.
 */

/**
 * The Spittr application has two essential domain concepts:
 *      spitters (the users of the application) and
 *      spittles (the brief status updates that users publish).
 */
public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
}
