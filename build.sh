fswatch $(find . -name "*.clj") | while read file; do
  echo "Change detected, rebuilding..."

  bb src/builds/html.clj > target/resume.html || echo "HTML Error"
  bb src/builds/markdown.clj > target/resume.md || echo "Markdown Error"
done
