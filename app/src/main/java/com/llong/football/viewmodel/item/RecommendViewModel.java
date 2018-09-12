package com.llong.football.viewmodel.item;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.llong.football.db.bean.Subject;

/**
 * Created by cui-hl on 2018/09/12.
 */

public class RecommendViewModel extends ViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> info = new ObservableField<>();


    public void setSubject(Subject data){
        if(data==null){
            title.set("-");
            info.set("...");
            return;
        }
        title.set(data.SubjectName);
        info.set(data.paperDetail.toString());
    }
}
