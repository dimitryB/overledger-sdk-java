package network.quant.util;

import java.util.List;

public class PagedResult<S> {

    private List<S> content;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private boolean last;
    private boolean first;
    private int totalPages;

}
