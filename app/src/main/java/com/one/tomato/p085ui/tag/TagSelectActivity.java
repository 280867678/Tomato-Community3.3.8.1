package com.one.tomato.p085ui.tag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.Fragment;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.one.tomato.adapter.PublishHotTagAdapter;
import com.one.tomato.adapter.TagSearchAdapter;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import com.tomatolive.library.utils.ConstantUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.one.tomato.ui.tag.TagSelectActivity */
/* loaded from: classes3.dex */
public class TagSelectActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private PublishHotTagAdapter hotTagAdapter;
    private LinearLayout liner_hot;
    private LinearLayout liner_search;
    private PublishHotTagAdapter recentTagAdapter;
    private RecyclerView recycler_hot;
    private RecyclerView recycler_recent;
    private RecyclerView recycler_search;
    private RelativeLayout relate_recent_add;
    private TagSearchAdapter tagSearchAdapter;
    private TextView text_add_tag;
    private ClearEditText tv_search;
    private ArrayList<Tag> selectTag = new ArrayList<>();
    private int position = 0;

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_tag_select;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public static void startActivity(Context context, ArrayList<Tag> arrayList, int i) {
        Intent intent = new Intent(context, TagSelectActivity.class);
        intent.putParcelableArrayListExtra("intent_list", arrayList);
        intent.putExtra("intent_num", i);
        ((Activity) context).startActivityForResult(intent, 4);
    }

    public static void startActivity(Fragment fragment, Context context, ArrayList<Tag> arrayList, int i) {
        Intent intent = new Intent(context, TagSelectActivity.class);
        intent.putParcelableArrayListExtra("intent_list", arrayList);
        intent.putExtra("intent_num", i);
        fragment.startActivityForResult(intent, 4);
    }

    public static void startActivity(Fragment fragment, Context context, ArrayList<Tag> arrayList, int i, int i2) {
        Intent intent = new Intent(context, TagSelectActivity.class);
        intent.putParcelableArrayListExtra("intent_list", arrayList);
        intent.putExtra("intent_num", i);
        intent.putExtra("intent_position", i2);
        fragment.startActivityForResult(intent, 4);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        requestTagList();
        this.position = getIntent().getIntExtra("intent_position", 0);
        ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("intent_list");
        if (parcelableArrayListExtra == null || parcelableArrayListExtra.isEmpty()) {
            return;
        }
        this.selectTag.addAll(parcelableArrayListExtra);
    }

    private void requestTagList() {
        ApiImplService.Companion.getApiImplService().requestHotTag().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.tag.TagSelectActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                TagSelectActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<Tag>>() { // from class: com.one.tomato.ui.tag.TagSelectActivity.1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<Tag> arrayList) {
                TagSelectActivity.this.hideWaitingDialog();
                TagSelectActivity.this.compareTagSelected(arrayList);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                TagSelectActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compareTagSelected(ArrayList<Tag> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        if (arrayList != null) {
            if (arrayList.size() > 50) {
                arrayList2.addAll(arrayList.subList(0, 50));
            } else {
                arrayList2.addAll(arrayList);
            }
        }
        ArrayList<Tag> tagList = DBUtil.getTagList();
        ArrayList arrayList3 = new ArrayList();
        if (this.selectTag.size() > 0 && arrayList2.size() > 0) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                Tag tag = (Tag) it2.next();
                Iterator<Tag> it3 = this.selectTag.iterator();
                while (it3.hasNext()) {
                    Tag next = it3.next();
                    if (next.getTagId() == tag.getTagId() && next.getTagName().equals(tag.getTagName())) {
                        tag.setSelect(true);
                    }
                }
            }
        }
        if (this.selectTag.size() > 0 && tagList != null && tagList.size() > 0) {
            Iterator<Tag> it4 = tagList.iterator();
            while (it4.hasNext()) {
                Tag next2 = it4.next();
                next2.setSelect(false);
                Iterator<Tag> it5 = this.selectTag.iterator();
                while (it5.hasNext()) {
                    Tag next3 = it5.next();
                    if (next3.getTagId() == next2.getTagId() && next3.getTagName().equals(next2.getTagName())) {
                        next2.setSelect(true);
                    }
                }
            }
        }
        if (arrayList2.size() > 0 && tagList != null && tagList.size() > 0) {
            Iterator it6 = arrayList2.iterator();
            while (it6.hasNext()) {
                Tag tag2 = (Tag) it6.next();
                Iterator<Tag> it7 = tagList.iterator();
                while (it7.hasNext()) {
                    Tag next4 = it7.next();
                    if (tag2.getTagId() == next4.getTagId() && tag2.getTagName().equals(next4.getTagName())) {
                        arrayList3.add(tag2);
                    }
                }
            }
        }
        if (arrayList2.size() > 0 && arrayList3.size() > 0) {
            Iterator it8 = arrayList3.iterator();
            while (it8.hasNext()) {
                arrayList2.remove((Tag) it8.next());
            }
        }
        if (tagList != null && tagList.size() > 0) {
            this.relate_recent_add.setVisibility(0);
            this.recycler_recent.setVisibility(0);
            this.recentTagAdapter.setNewData(tagList);
        }
        this.hotTagAdapter.setNewData(arrayList2);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("intent_list", this.selectTag);
        intent.putExtra("intent_position", this.position);
        setResult(-1, intent);
        super.onBackPressed();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        this.liner_hot = (LinearLayout) findViewById(R.id.liner_hot);
        this.liner_search = (LinearLayout) findViewById(R.id.liner_search);
        this.recycler_search = (RecyclerView) findViewById(R.id.recycler_search);
        this.text_add_tag = (TextView) findViewById(R.id.text_add_tag);
        this.tv_search = (ClearEditText) findViewById(R.id.tv_search);
        this.relate_recent_add = (RelativeLayout) findViewById(R.id.relate_recent_add);
        this.liner_hot.setVisibility(0);
        this.liner_search.setVisibility(8);
        this.recycler_hot = (RecyclerView) findViewById(R.id.recycler_hot);
        this.hotTagAdapter = new PublishHotTagAdapter(null);
        this.recycler_hot.setAdapter(this.hotTagAdapter);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getMContext());
        flexboxLayoutManager.setFlexWrap(1);
        flexboxLayoutManager.setAlignItems(0);
        this.recycler_hot.setLayoutManager(flexboxLayoutManager);
        this.recycler_recent = (RecyclerView) findViewById(R.id.recycler_recent);
        this.recentTagAdapter = new PublishHotTagAdapter(null);
        this.recycler_recent.setAdapter(this.recentTagAdapter);
        FlexboxLayoutManager flexboxLayoutManager2 = new FlexboxLayoutManager(getMContext());
        flexboxLayoutManager2.setFlexWrap(1);
        flexboxLayoutManager2.setAlignItems(0);
        this.recycler_recent.setLayoutManager(flexboxLayoutManager2);
        this.tagSearchAdapter = new TagSearchAdapter();
        this.recycler_search.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        this.recycler_search.setAdapter(this.tagSearchAdapter);
        this.tagSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.ui.tag.TagSelectActivity.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                TagSelectActivity.this.addTagToSelect((Tag) baseQuickAdapter.getItem(i));
            }
        });
        this.hotTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.ui.tag.TagSelectActivity.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                TagSelectActivity.this.addTagToSelect((Tag) baseQuickAdapter.getItem(i));
            }
        });
        this.recentTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.ui.tag.TagSelectActivity.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Tag tag = (Tag) baseQuickAdapter.getItem(i);
                if (!tag.isSelect()) {
                    TagSelectActivity.this.selectTag.add(tag);
                    TagSelectActivity.this.onBackPressed();
                    return;
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_tag_chongfu));
            }
        });
        findViewById(R.id.text_ok).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.tag.TagSelectActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TagSelectActivity.this.liner_search.getVisibility() == 0) {
                    TagSelectActivity.this.liner_search.setVisibility(8);
                    TagSelectActivity.this.liner_hot.setVisibility(0);
                    return;
                }
                TagSelectActivity.this.onBackPressed();
            }
        });
        findViewById(R.id.image_delete).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.tag.TagSelectActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                List<Tag> data = TagSelectActivity.this.recentTagAdapter.getData();
                ArrayList arrayList = new ArrayList();
                List<Tag> data2 = TagSelectActivity.this.hotTagAdapter.getData();
                if (data2.size() < 50) {
                    arrayList.addAll(data2);
                    arrayList.addAll(data);
                    if (arrayList.size() > 50) {
                        TagSelectActivity.this.hotTagAdapter.setNewData(arrayList.subList(0, 50));
                    } else {
                        arrayList.addAll(data);
                        TagSelectActivity.this.hotTagAdapter.setNewData(arrayList);
                    }
                }
                data.clear();
                TagSelectActivity.this.recentTagAdapter.notifyDataSetChanged();
                DBUtil.deleteTag();
            }
        });
        this.tv_search.setOnTextClearListener(new ClearEditText.OnTextClearListener(this) { // from class: com.one.tomato.ui.tag.TagSelectActivity.8
            @Override // com.one.tomato.widget.ClearEditText.OnTextClearListener
            public void onTextClear() {
            }
        });
        this.tv_search.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.tag.TagSelectActivity.9
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                TagSelectActivity.this.requestTagSearch(editable.toString().trim());
            }
        });
        this.text_add_tag.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.tag.TagSelectActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TagSelectActivity.this.addTagToSelect(new Tag(TagSelectActivity.this.tv_search.getText().toString().trim()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTagToSelect(Tag tag) {
        boolean z;
        if (tag.isSelect()) {
            ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_tag_chongfu));
            return;
        }
        if (this.selectTag.size() > 0) {
            Iterator<Tag> it2 = this.selectTag.iterator();
            while (it2.hasNext()) {
                Tag next = it2.next();
                if (next.getTagId() == tag.getTagId() && next.getTagName().equals(tag.getTagName())) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_tag_chongfu));
                    return;
                }
            }
        }
        this.selectTag.add(tag);
        ArrayList arrayList = (ArrayList) this.recentTagAdapter.getData();
        if (arrayList.size() > 0) {
            Iterator it3 = arrayList.iterator();
            z = false;
            while (it3.hasNext()) {
                Tag tag2 = (Tag) it3.next();
                if (tag2.getTagId() == tag.getTagId() && tag2.getTagName().equals(tag.getTagName())) {
                    z = true;
                }
                tag2.setSelect(false);
            }
        } else {
            z = false;
        }
        if (!z) {
            try {
                if (arrayList.size() < 8) {
                    arrayList.add(tag);
                } else {
                    Tag tag3 = (Tag) arrayList.get(0);
                    DBUtil.deleteTag(tag3.getTagName(), tag3.getTagId());
                    arrayList.add(tag);
                }
                DBUtil.setTagList(arrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestTagSearch(String str) {
        ApiImplService.Companion.getApiImplService().requestSearchTag(str).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<Tag>>() { // from class: com.one.tomato.ui.tag.TagSelectActivity.11
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<Tag> arrayList) {
                TagSelectActivity.this.hideWaitingDialog();
                TagSelectActivity.this.searchResult(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchResult(ArrayList<Tag> arrayList) {
        boolean z;
        String trim = this.tv_search.getText().toString().trim();
        if (arrayList != null && arrayList.size() > 0) {
            this.tagSearchAdapter.setNewData(arrayList);
            Iterator<Tag> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                if (it2.next().getTagName().equals(trim)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        this.liner_search.setVisibility(0);
        this.liner_hot.setVisibility(8);
        if (z || trim.length() <= 1) {
            this.text_add_tag.setVisibility(8);
            return;
        }
        this.text_add_tag.setVisibility(0);
        TextView textView = this.text_add_tag;
        textView.setText(AppUtil.getString(R.string.publish_tag_add) + ConstantUtils.PLACEHOLDER_STR_ONE + trim);
    }
}
