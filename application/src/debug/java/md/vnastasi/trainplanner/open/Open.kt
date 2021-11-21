package md.vnastasi.trainplanner.open

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class Open

@Open
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting
