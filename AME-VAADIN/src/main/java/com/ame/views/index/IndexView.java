package com.ame.views.index;

import com.ame.base.BaseView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route("index")
public class IndexView extends BaseView {


    @Override
    protected void initView() {
        Span span = new Span("Hello World");
        setCompositionRoot(span);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public String getPageTitle() {
        return null;
    }
}
