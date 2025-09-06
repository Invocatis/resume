
function build_markdown() {
    echo "Building Markdown"
    MARKDOWN="$(bb src/builds/markdown.clj)"
    (echo "$MARKDOWN" > target/resume.md) || echo "Markdown Error"
}

function build_docx() {
    echo "Building Docx"
    MARKDOWN="$(bb src/builds/markdown.clj)"
    (echo "$MARKDOWN" > target/resume.txt) || echo "Docx Error"
}

function build_txt() {
    echo "Building TXT"
    MARKDOWN="$(bb src/builds/markdown.clj)"
    (echo "$MARKDOWN" > target/resume.txt) || echo "Docx Error"
}

function build_html() {
    echo "Building HTML"
    HTML="$(bb src/builds/html.clj)"
    (echo "$HTML" > target/resume.html)  || echo "HTML Error"
}

function build_pdf() {
    echo "Building PDF"
    build_html
    wkhtmltopdf target/resume.html target/resume.pdf
}

function build_all() {
    build_markdown
    build_txt
    build_docx
    build_pdf
}

function build() {

    case $1 in
      markdown)
        build_markdown
        ;;
      txt)
        build_txt
        ;;
      html)
        build_html
        ;;
      pdf)
        build_pdf
        ;;
      docx)
        build_docx
        ;;
      *)
        build_all
        ;;
    esac

 }

case $1 in

  watch)
    echo "Building once, then watching ..."
    build $2
    fswatch $(find . -name "*.clj") $(find resources) | while read file; do
      echo "Change detected, rebuilding..."
      build
    done
    ;;

  *)
    build $1
    ;;

esac
