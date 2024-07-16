package com.mitch.androidutils.utils.mime

/**
 * See [Media Types](https://www.iana.org/assignments/media-types/media-types.xhtml) of IANA.
 */
object MimeTypes {

    enum class Application(val extensions: Array<String>, val mimeType: String) {
        /** AbiWord document */
        AbiWord(arrayOf(".abw"), "application/x-abiword"),

        /** Archive document (multiple files embedded) */
        Archive(arrayOf(".arc"), "application/x-freearc"),

        /** Atom Documents */
        AtomXml(arrayOf(".atom"), "application/atom+xml"),

        /** Atom Category Documents */
        AtomcatXml(arrayOf(".atomcat"), "application/atomcat+xml"),

        /** Amazon Kindle eBook format */
        AmazonKindle(arrayOf(".azw"), "application/vnd.amazon.ebook"),

        /** Apple Installer Package */
        AppleInstaller(arrayOf(".mpkg"), "application/vnd.apple.installer+xml"),

        /** C-Shell script */
        Csh(arrayOf(".csh"), "application/x-csh"),

        /** ECMAScript */
        Ecmascript(arrayOf(".es"), "application/ecmascript"),

        /** Electronic publication (EPUB) */
        Epub(arrayOf(".epub"), "application/epub+zip"),

        /** GZip Compressed Archive */
        Gzip(arrayOf(".gz"), "application/gzip"),

        /** Java Archive (JAR) */
        JavaArchive(arrayOf(".jar"), "application/java-archive"),

        /** JavaScript */
        Javascript(arrayOf(".js"), "application/javascript"),

        /** JSON format */
        Json(arrayOf(".json"), "application/json"),

        /** JSON-LD format */
        JsonLd(arrayOf(".jsonld"), "application/ld+json"),

        /** Microsoft Excel */
        MsExcel(arrayOf(".xls"), "application/vnd.ms-excel"),

        /** Microsoft Excel (OpenXML) */
        MsExcelOpenXml(arrayOf(".xlsx"), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

        /** MS Embedded OpenType fonts */
        MsOpentypeFonts(arrayOf(".eot"), "application/vnd.ms-fontobject"),

        /** Microsoft PowerPoint */
        MsPowerPoint(arrayOf(".ppt"), "application/vnd.ms-powerpoint"),

        /** Microsoft PowerPoint (OpenXML) */
        MsPowerPointOpenXml(arrayOf(".pptx"), "application/vnd.openxmlformats-officedocument.presentationml.presentation"),

        /** Microsoft Word */
        MsWord(arrayOf(".doc"), "application/msword"),

        /** Microsoft Word (OpenXML) */
        MsWordOpenXml(arrayOf(".docx"), "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

        /** Microsoft Visio */
        MsVisio(arrayOf(".vsd"), "application/vnd.visio"),

        /** Any kind of binary data */
        OctetStream(arrayOf(".bin"), "application/octet-stream"),

        /** OGG */
        Ogg(arrayOf(".ogx"), "application/ogg"),

        /** OpenDocument presentation document */
        OpenDocumentPresentation(arrayOf(".odp"), "application/vnd.oasis.opendocument.presentation"),

        /** OpenDocument spreadsheet document */
        OpenDocumentSpreadsheet(arrayOf(".ods"), "application/vnd.oasis.opendocument.spreadsheet"),

        /** OpenDocument text document */
        OpenDocumentText(arrayOf(".odt"), "application/vnd.oasis.opendocument.text"),

        /** Adobe Portable Document Format (PDF) */
        Pdf(arrayOf(".pdf"), "application/pdf"),

        /** Hypertext Preprocessor (Personal Home Page) */
        Php(arrayOf(".php"), "application/x-httpd-php"),

        /** PKCS #10 */
        Pkcs10(arrayOf(".p10"), "application/pkcs10"),

        /** PKCS #7 MIME */
        Pkcs7Mime(arrayOf(".p7m"), "application/pkcs7-mime"),

        /** PKCS #7 Signature */
        Pkcs7Signature(arrayOf(".p7s"), "application/pkcs7-signature"),

        /** PKCS #8 */
        Pkcs8(arrayOf(".p8"), "application/pkcs8"),

        /** PKCS #12 */
        Pkcs12(arrayOf(".p12", ".pfx"), "application/x-pkcs12"),

        /** PostScript */
        Postscript(arrayOf(".ps"), "application/postscript"),

        /** RAR archive */
        Rar(arrayOf(".rar"), "application/vnd.rar"),

        /** RDF/XML */
        RdfXml(arrayOf(".rdf"), "application/rdf+xml"),

        /** Rich Text Format (RTF) */
        Rtf(arrayOf(".rtf"), "application/rtf"),

        /** 7-zip archive */
        SevenZip(arrayOf(".7z"), "application/x-7z-compressed"),

        /** Bourne shell script */
        Sh(arrayOf(".sh"), "application/x-sh"),

        /** SMIL documents */
        SmilXml(arrayOf(".smil", ".smi", ".sml"), "application/smil+xml"),

        /** SQL */
        Sql(arrayOf(".sql"), "application/sql"),

        /** Small web format (SWF) or Adobe Flash document */
        Swf(arrayOf(".swf"), "application/x-shockwave-flash"),

        /** Tape Archive (TAR) */
        Tar(arrayOf(".tar"), "application/x-tar"),

        /** BZip Archive */
        XBzip(arrayOf(".bz"), "application/x-bzip"),

        /** BZip2 Archive */
        XBzip2(arrayOf(".bz2"), "application/x-bzip2"),

        /** XHTML */
        XhtmlXml(arrayOf(".xhtml"), "application/xhtml+xml"),

        /** XML */
        Xml(arrayOf(".xml"), "application/xml"),

        /** XML DTD */
        XmlDtd(arrayOf(".dtd", ".mod"), "application/xml-dtd"),

        /** XSLT Document */
        XsltXml(arrayOf(".xsl", ".xslt"), "application/xslt+xml"),

        /** XUL */
        Xul(arrayOf(".xul"), "application/vnd.mozilla.xul+xml"),

        /** Zip Archive */
        Zip(arrayOf(".zip"), "application/zip")
    }

    enum class Audio(val extensions: Array<String>, val mimeType: String) {
        /** AAC audio */
        Aac(arrayOf(".aac"), "audio/aac"),

        /** Musical Instrument Digital Interface (MIDI) */
        Midi(arrayOf(".mid", ".midi"), "audio/midi"),

        /** Musical Instrument Digital Interface (MIDI) */
        XMidi(arrayOf(".mid", ".midi"), "audio/x-midi"),

        /** Matroska Multimedia Container */
        Mka(arrayOf(".mka"), "audio/x-matroska"),

        /** MP3 audio */
        Mp3(arrayOf(".mp3"), "audio/mpeg"),

        /** MP4 audio */
        Mp4(arrayOf(".mp4"), "audio/mp4"),

        /** OGG audio */
        Ogg(arrayOf(".oga"), "audio/ogg"),

        /** Opus audio */
        Opus(arrayOf(".opus"), " audio/opus"),

        /** Waveform Audio Format */
        Wav(arrayOf(".wav"), "audio/wav"),

        /** WEBM audio */
        Webm(arrayOf(".weba"), "audio/webm")
    }

    enum class Image(val extensions: Array<String>, val mimeType: String) {
        /** Windows OS/2 Bitmap Graphics */
        Bmp(arrayOf(".bmp"), "image/bmp"),

        /** Graphics Interchange Format (GIF)*/
        Gif(arrayOf(".gif"), "image/gif"),

        /** Icon format */
        Icon(arrayOf(".ico"), "image/vnd.microsoft.icon"),

        /** JPEG images */
        Jpeg(arrayOf(".jpg", ".jpeg"), "image/jpeg"),

        /** Portable Network Graphics */
        Png(arrayOf(".png"), "image/png"),

        /** Scalable Vector Graphics (SVG) */
        SvgXml(arrayOf(".svg"), "image/svg+xml"),

        /** Tagged Image File Format (TIFF) */
        Tiff(arrayOf(".tif", ".tiff"), "image/tiff"),

        /** WEBP image */
        Webp(arrayOf(".webp"), "image/webp"),

        /** HEIC image */
        Heic(arrayOf(".heic"), "image/heic"),

        /** HEIF image */
        Heif(arrayOf(".heif"), "image/heif"),

        /** AVIF image */
        Avif(arrayOf(".avif", ".avifs"), "image/avif"),
    }

    enum class Text(val extensions: Array<String>, val mimeType: String) {
        /** Cascading Style Sheets (CSS) */
        Css(arrayOf(".css"), "text/css"),

        /** Comma-separated values (CSV) */
        Csv(arrayOf(".csv"), "text/csv"),

        /** HyperText Markup Language (HTML) */
        Html(arrayOf(".htm", ".html"), "text/html"),

        /** iCalendar format */
        Icalendar(arrayOf(".ics"), "text/calendar"),

        /** JavaScript */
        JavascriptModule(arrayOf(".mjs"), "text/javascript"),

        /** Text, (generally ASCII or ISO 8859-n) */
        Plain(arrayOf(".txt"), "text/plain"),

        /** Standard Generalized Markup Language */
        Sgml(arrayOf(".sgml"), "text/sgml"),

        /** YAML */
        Yaml(arrayOf(".yml", ".yaml"), "text/yaml"),
    }

    enum class Video(val extensions: Array<String>, val mimeType: String) {
        /** AVI: Audio Video Interleave */
        Avi(arrayOf(".avi"), "video/x-msvideo"),

        /** 3GPP audio/video container */
        Threegpp(arrayOf(".3gp"), "video/3gpp"),

        /** 3GPP2 audio/video container */
        Threegpp2(arrayOf(".3g2"), "video/3gpp2"),

        /** Matroska Multimedia Container */
        Mkv(arrayOf(".mkv"), "video/x-matroska"),

        /** MP4 video */
        Mp4(arrayOf(".mp4"), "video/mp4"),

        /** MPEG Video */
        Mpeg(arrayOf(".mpg", ".mpeg"), "video/mpeg"),

        /** MPEG transport stream */
        MpegTs(arrayOf(".ts"), "video/mp2t"),

        /** OGG video */
        Ogg(arrayOf(".ogv"), "video/ogg"),

        /** QuickTime */
        Quicktime(arrayOf(".mov", ".qt"), "video/quicktime"),

        /** WEBM video */
        Webm(arrayOf(".webm"), "video/webm")
    }

    enum class Font(val extensions: Array<String>, val mimeType: String) {
        /** OpenType font */
        Otf(arrayOf(".otf"), "font/otf"),

        /** TrueType Font */
        Ttf(arrayOf(".ttf"), "font/ttf"),

        /** Web Open Font Format (WOFF) */
        Woff(arrayOf(".woff"), "font/woff"),

        /** Web Open Font Format (WOFF) */
        Woff2(arrayOf(".woff2"), "font/woff2")
    }
}
