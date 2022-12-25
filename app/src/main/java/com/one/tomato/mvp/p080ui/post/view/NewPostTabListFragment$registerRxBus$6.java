package com.one.tomato.mvp.p080ui.post.view;

import com.one.tomato.entity.event.PostCollectOrThumbEvent;
import io.reactivex.functions.Consumer;

/* compiled from: NewPostTabListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$registerRxBus$6 */
/* loaded from: classes3.dex */
final class NewPostTabListFragment$registerRxBus$6<T> implements Consumer<PostCollectOrThumbEvent> {
    public static final NewPostTabListFragment$registerRxBus$6 INSTANCE = new NewPostTabListFragment$registerRxBus$6();

    NewPostTabListFragment$registerRxBus$6() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(PostCollectOrThumbEvent postCollectOrThumbEvent) {
        boolean z = postCollectOrThumbEvent instanceof PostCollectOrThumbEvent;
    }
}
