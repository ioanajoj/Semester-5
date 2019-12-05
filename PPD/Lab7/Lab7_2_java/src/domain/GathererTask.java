package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author joj on 11/26/2019
 **/
public class GathererTask implements Runnable {
    private List<IntPositionPair> queue;
    private BigNumber bigNumber;
    private Optional<GathererTask> provider;
    private Optional<GathererTask> consumer;
    private boolean finished = false;

    public GathererTask(BigNumber bigNumber, GathererTask provider) {
        this.queue = new ArrayList<>();
        this.bigNumber = bigNumber;
        if (provider == null)
            this.provider = Optional.empty();
        else
            this.provider = Optional.of(provider);
    }

    public void enqueue(IntPositionPair pair) {
        this.queue.add(pair);
    }

    public void signalFinished() {
        this.finished = true;
    }

    @Override
    public void run() {
        while(!finished) {
            if (this.queue.size() > 0) {
                IntPositionPair intPositionPair = this.queue.get(0);

            }
        }
    }
}
