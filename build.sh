
function build() {


  HTML="$(bb src/builds/html.clj)"
  MARKDOWN="$(bb src/builds/markdown.clj)"

  (echo "$HTML" > target/resume.html)  || echo "HTML Error"
  (echo "$MARKDOWN" > target/resume.md) || echo "Markdown Error"
  (echo "$MARKDOWN" > target/resume.txt) || echo "Docx Error"

  echo "$MARKDOWN" | pandoc -o target/resume.docx
}

case $1 in

  watch)
    echo "Building once, then watching ..."
    build
    fswatch $(find . -name "*.clj") $(find resources) | while read file; do
      echo "Change detected, rebuilding..."
      build
    done
    ;;

  markdown)
    MARKDOWN="$(bb src/builds/markdown.clj)"
    (echo "$MARKDOWN" > target/resume.md) || echo "Markdown Error"
    ;;
  txt)
    MARKDOWN="$(bb src/builds/markdown.clj)"
    (echo "$MARKDOWN" > target/resume.txt) || echo "Txt Error"
    ;;
  html)
    HTML="$(bb src/builds/html.clj)"
    (echo "$HTML" > target/resume.html)  || echo "HTML Error"
    ;;
  docx)
    MARKDOWN="$(bb src/builds/markdown.clj)"
    echo "$MARKDOWN" | pandoc -o target/resume.docx
    ;;
  *)
    build
    ;;
esac