package moe.notify.animenotifier.domain.interactors.base;

import moe.notify.animenotifier.domain.executor.Executor;
import moe.notify.animenotifier.domain.executor.MainThread;

/**
 * <p/>
 * This abstract class implements some common methods for all interactors. Cancelling an interactor,
 * check if its running
 * and finishing an interactor has mostly the same code throughout so that is why this class was
 * created. Field methods
 * are declared volatile as we might use these methods from different threads (mainly from UI).
 * <p/>
 * For example, when an activity is getting destroyed then we should probably cancel an interactor
 * but the request will come from the UI thread unless the request was specifically assigned to a
 * background thread.
 */
public abstract class AbstractInteractor implements Interactor {

  protected final MainThread mainThread;
  private final Executor threadExecutor;
  private volatile boolean isCanceled;
  private volatile boolean isRunning;

  protected AbstractInteractor(Executor threadExecutor, MainThread mainThread) {
    this.threadExecutor = threadExecutor;
    this.mainThread = mainThread;
  }

  /**
   * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED
   * DIRECTLY but, instead, a
   * developer should call the execute() method of an interactor to make sure the operation is done
   * on a background thread.
   * <p/>
   * This method should only be called directly while doing unit/integration tests. That is the only
   * reason it is declared
   * public as to help with easier testing.
   */
  public abstract void run();

  public void cancel() {
    isCanceled = true;
    isRunning = false;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public void onFinished() {
    isRunning = false;
    isCanceled = false;
  }

  @Override public void execute() {

    // mark this interactor as running
    isRunning = true;

    // start running this interactor in a background thread
    threadExecutor.execute(this);
  }
}
