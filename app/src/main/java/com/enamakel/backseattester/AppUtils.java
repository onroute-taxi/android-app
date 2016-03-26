package com.enamakel.backseattester;


import android.accounts.Account;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.IntentCompat;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enamakel.backseattester.util.AlertDialogBuilder;
import com.enamakel.backseattester.util.ScrollAwareFABBehavior;


public class AppUtils {
    static final String ABBR_YEAR = "y";
    static final String ABBR_WEEK = "w";
    static final String ABBR_DAY = "d";
    static final String ABBR_HOUR = "h";
    static final String ABBR_MINUTE = "m";
    static final String PLAY_STORE_URL = "market://details?id=" + BuildConfig.APPLICATION_ID;


    public static void setTextWithLinks(TextView textView, String htmlText) {
        setHtmlText(textView, htmlText);
        // TODO https://code.google.com/p/android/issues/detail?id=191430
        textView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    TextView widget = (TextView) v;
                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = Spannable.Factory.getInstance()
                            .newSpannable(widget.getText())
                            .getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) link[0].onClick(widget);
                        return true;
                    }
                }
                return false;
            }
        });
    }


    public static void setHtmlText(TextView textView, String htmlText) {
        textView.setText(TextUtils.isEmpty(htmlText) ? null : trim(Html.fromHtml(htmlText)));
    }


    public static Intent makeEmailIntent(String subject, String text) {
        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }


//    public static void openExternal(@NonNull final Context context,
//                                    @NonNull AlertDialogBuilder alertDialogBuilder,
//                                    @NonNull final StoryModel item) {
//        if (TextUtils.isEmpty(item.getURL().toString()) ||
//                item.getURL().toString().startsWith(HackerNewsClient.BASE_WEB_URL)) {
//            openWebUrlExternal(context,
//                    item.getTitle(),
//                    String.format(HackerNewsClient.WEB_ITEM_PATH, item.getId()));
//            return;
//        }
//        openWebUrlExternal(context,
//                item.getTitle(),
//                item.getURL().toString());
////        alertDialogBuilder
////                .init(context)
////                .setMessage(R.string.view_in_browser)
////                .setPositiveButton(R.string.article, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        openWebUrlExternal(context,
////                                item.getTitle(),
////                                item.getUrl());
////                    }
////                })
////                .setNegativeButton(R.string.comments, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        openWebUrlExternal(context,
////                                item.getTitle(),
////                                String.format(HackerNewsClient.WEB_ITEM_PATH, item.getId()));
////                    }
////                })
////                .create()
////                .show();
//
//    }


//    public static void share(@NonNull final Context context,
//                             @NonNull AlertDialogBuilder alertDialogBuilder,
//                             @NonNull final StoryModel item) {
//        if (TextUtils.isEmpty(item.getURL().toString()) ||
//                item.getUrl().startsWith(HackerNewsClient.BASE_WEB_URL)) {
//            context.startActivity(makeChooserShareIntent(context,
//                    item.getTitle(),
//                    String.format(HackerNewsClient.WEB_ITEM_PATH, item.getId())));
//            return;
//        }
//        context.startActivity(makeChooserShareIntent(context,
//                item.getTitle(),
//                item.getURL().toString()));
////        alertDialogBuilder
////                .init(context)
////                .setMessage(R.string.share)
////                .setPositiveButton(R.string.article, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        context.startActivity(makeChooserShareIntent(context,
////                                item.getTitle(),
////                                item.getUrl()));
////                    }
////                })
////                .setNegativeButton(R.string.comments, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        context.startActivity(makeChooserShareIntent(context,
////                                item.getDisplayedTitle(),
////                                String.format(HackerNewsClient.WEB_ITEM_PATH, item.getId())));
////                    }
////                })
////                .create()
////                .show();
//    }


    public static int getThemedResId(Context context, @AttrRes int attr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{attr});
        final int resId = typedArray.getResourceId(0, 0);
        typedArray.recycle();
        return resId;
    }


    public static float getDimension(Context context, @StyleRes int styleResId, @AttrRes int attr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(styleResId, new int[]{attr});
        float size = typedArray.getDimension(0, 0);
        typedArray.recycle();
        return size;
    }


    public static int getDimensionInDp(Context context, @DimenRes int dimenResId) {
        return (int) (context.getResources().getDimension(dimenResId) /
                context.getResources().getDisplayMetrics().density);
    }


    public static void restart(Activity activity) {
        activity.finish();
        final Intent intent = activity.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }


    public static String getAbbreviatedTimeSpan(long timeMillis) {
        long span = Math.max(System.currentTimeMillis() - timeMillis, 0);
        if (span >= DateUtils.YEAR_IN_MILLIS) return (span / DateUtils.YEAR_IN_MILLIS) + ABBR_YEAR;
        if (span >= DateUtils.WEEK_IN_MILLIS) return (span / DateUtils.WEEK_IN_MILLIS) + ABBR_WEEK;
        if (span >= DateUtils.DAY_IN_MILLIS) return (span / DateUtils.DAY_IN_MILLIS) + ABBR_DAY;
        if (span >= DateUtils.HOUR_IN_MILLIS) return (span / DateUtils.HOUR_IN_MILLIS) + ABBR_HOUR;
        return (span / DateUtils.MINUTE_IN_MILLIS) + ABBR_MINUTE;
    }


    public static boolean isOnWiFi(Context context) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting() &&
                activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }


//    public static Pair<String, String> getCredentials(Context context) {
//        String username = Preferences.getUsername(context);
//        if (TextUtils.isEmpty(username)) return null;
//
//        AccountManager accountManager = AccountManager.get(context);
//        Account[] accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID);
//        for (Account account : accounts)
//            if (TextUtils.equals(username, account.name))
//                return Pair.create(username, accountManager.getPassword(account));
//
//        return null;
//    }


//    /**
//     * Displays UI to allow user to login
//     * If no accounts exist in user's device, regardless of login status, prompt to login again
//     * If 1 or more accounts in user's device, and already logged in, prompt to update password
//     * If 1 or more accounts in user's device, and logged out, show account chooser
//     *
//     * @param context            activity context
//     * @param alertDialogBuilder dialog builder
//     */
//    public static void showLogin(Context context, AlertDialogBuilder alertDialogBuilder) {
//        Account[] accounts = AccountManager.get(context).getAccountsByType(BuildConfig.APPLICATION_ID);
//
//        // no accounts, ask to login or re-login
//        if (accounts.length == 0) context.startActivity(new Intent(context, LoginActivity.class));
//
//        else if (!TextUtils.isEmpty(Preferences.getUsername(context)))
//            // stale account, ask to re-login
//            context.startActivity(new Intent(context, LoginActivity.class));
//
//        else
//            // logged out, choose from existing accounts to log in
//            showAccountChooser(context, alertDialogBuilder, accounts);
//
//    }
//
//
//    public static void registerAccountsUpdatedListener(final Context context) {
//        AccountManager.get(context).addOnAccountsUpdatedListener(new OnAccountsUpdateListener() {
//            @Override
//            public void onAccountsUpdated(Account[] accounts) {
//                String username = Preferences.getUsername(context);
//                if (TextUtils.isEmpty(username)) return;
//
//                for (Account account : accounts)
//                    if (TextUtils.equals(account.name, username)) return;
//
//                Preferences.setUsername(context, null);
//            }
//        }, null, true);
//    }


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void openPlayStore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URL));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        else intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, R.string.no_playstore, Toast.LENGTH_SHORT).show();
        }
    }


    public static void showAccountChooser(final Context context, AlertDialogBuilder alertDialogBuilder,
                                          Account[] accounts) {
        final String[] items = new String[accounts.length + 1];

//        items[items.length - 1] = context.getString(R.string.add_account);
//        alertDialogBuilder
//                .init(context)
//                .setTitle(R.string.choose_account)
//                .setSingleChoiceItems(items, checked, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == items.length - 1) {
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            intent.putExtra(LoginActivity.EXTRA_ADD_ACCOUNT, true);
//                            context.startActivity(intent);
//                        } else {
//                            Preferences.setUsername(context, items[which]);
//                            Toast.makeText(context,
//                                    context.getString(R.string.welcome, items[which]),
//                                    Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                        dialog.dismiss();
//                    }
//                })
//                .show();
    }


    public static void toggleFab(FloatingActionButton fab, boolean visible) {
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        if (visible) {
            fab.show();
            p.setBehavior(new ScrollAwareFABBehavior());
        } else {
            fab.hide();
            p.setBehavior(null);
        }
    }


    static CharSequence trim(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) return charSequence;
        int end = charSequence.length() - 1;

        while (Character.isWhitespace(charSequence.charAt(end))) end--;
        return charSequence.subSequence(0, end + 1);
    }


    static Intent makeShareIntent(String subject, String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }


    static Intent makeChooserShareIntent(Context context, String subject, String text) {
        Intent shareIntent = AppUtils.makeShareIntent(subject, text);
        Intent chooserIntent = Intent.createChooser(shareIntent, context.getString(R.string.share));
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return chooserIntent;
    }


    public static class ShareBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.startActivity(makeChooserShareIntent(context,
                    intent.getStringExtra(Intent.EXTRA_SUBJECT),
                    intent.getStringExtra(Intent.EXTRA_TEXT)));
        }
    }
}