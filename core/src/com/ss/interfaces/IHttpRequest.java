package com.ss.interfaces;

import com.ss.object.Data.FarmModel;

import java.util.List;

public interface IHttpRequest {
    void finished(List<FarmModel> lsFarm);
}
