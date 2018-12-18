package network.quant.util;

import java.util.List;

public interface PagedResult<S> {

    List<S> getContent();

    int getPageNumber();

    int getPageSize();

    int getTotalElements();

    boolean isLast();

    boolean isFirst();

    int getTotalPages();

}
