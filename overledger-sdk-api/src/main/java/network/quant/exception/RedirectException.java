package network.quant.exception;

/**
 * Throw this exception when transaction does not have Quant data
 */
public class RedirectException extends RuntimeException {

    private String url;

    public RedirectException(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

}
