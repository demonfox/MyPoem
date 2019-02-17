package org.demonfox.mypoem;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PoemQuery {

    private Disposable disposable;
    private StringBuilder poemText = new StringBuilder();
    private EditText poemEditText;
    private EditText titleEditText;
    private EditText dynastyEditText;
    private EditText authorEditText;

    private DisposableObserver<Poem> getAzureTableObserver() {
        return new DisposableObserver<Poem>() {
            @Override
            public void onNext(Poem s) {
                poemEditText.setText(s.getFullText());
                titleEditText.setText(s.getTitle());
                dynastyEditText.setText(s.getDynasty());
                authorEditText.setText(s.getAuthor());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };
    }

    private Observable<Poem> getAzureTableObservable() {
        //return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
        Observable<Poem> azureTableObservable = Observable.create(new ObservableOnSubscribe<Poem>() {
            @Override
            public void subscribe(ObservableEmitter<Poem> emitter) throws Exception {
                try {
                    //List<String> todos = Arrays.asList("从军令11", "【唐】", "王昌龄");
                    Poem poem = new Poem();
                    poem.setTitle("念奴娇 - 赤壁怀古");
                    poem.setDynasty("【宋】");
                    poem.setAuthor("苏轼");
                    poem.setFullText("大江东去，浪淘尽，千古风流人物。\n" +
                            "故垒西边，人道是，三国周郎赤壁。\n" +
                            "乱石穿空，惊涛拍岸，卷起千堆雪。\n" +
                            "江山如画，一时多少豪杰。");

                    if (!emitter.isDisposed())
                        emitter.onNext(poem);

                    if (!emitter.isDisposed())
                        emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

        return azureTableObservable;
    }

    public Disposable updatePoemText(EditText p, EditText t, EditText d, EditText a) {

        poemEditText = p;
        titleEditText = t;
        dynastyEditText = d;
        authorEditText = a;

        // observable
        Observable<Poem> azureTableObservable = getAzureTableObservable();

        // observer
        DisposableObserver<Poem> azureTableObserver = getAzureTableObserver();

        return azureTableObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(azureTableObserver);
    }
}
