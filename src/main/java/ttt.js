var n = VideoInfoList.split("$");
if ("mp4" == n[4])
    var ff = n[3], ss = 0, aa = n[3];
else if ("m3u8" == n[4])
    var ff = "/ckplayer/m3u8.swf", ss = 4,
    aa = n[3];
var flashvars = {f: ff, a: aa, c: 0, p: 1, s: ss},
    params = {bgcolor: "#FFF",
        allowFullScreen: !0,
        allowScriptAccess: "always",
        wmode: "transparent"},
    video = ["" + n[3] + "->video/mp4"],
    support = ["iPad", "iPhone", "ios", "android+false", "msie10+false"];
CKobject.embed("/ckplayer/ckplayer.swf", "a1", "ckplayer_a1", "100%", "100%", !1, flashvars, video, params);