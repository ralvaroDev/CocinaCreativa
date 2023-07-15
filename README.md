Arquitectura
La arquitectura esta construida a base de Android Architecture Components y sigue las recomendaciones brindadas por las guias de arquitectura de Google. Se prioriza la separacion de responsabilidades usando las siguientes capas:
Ui -> ViewModel -> UseCase -> Repositorios -> DataSources -> Library Implementations

Logrando asi mantener la logica separada de las Actividades y Fragmentos, realizando la minima logica aun en los ViewModels y priorizando hacer estos en las capas de los UseCase y Repositorios.
La observacion de datos se realiza a base de Flows y StateFlows. 

Datos iniciales que describen las principales librerias de la app:
- Se usa Navigation Components para la navegacion en la app, en conjunto con el complemento SafeArgs para el traspaso de datos entre fragments
- Se usa como inyector de dependencias Hilt
- Room es usado como mecanismo de busqueda, aprovechando la busqueda de texto completo usando Fts4 para poder filtrar a traves de propiedades clave
- Para los test unitarios se usa Juni4 con Mockito y Mockito.kt en ciertos casos
- Se usa la libreria de Spash Screen recomendada por google para la presentacion de la app en conjunto con el Launch Activity
- Coil para la descarga de imagenes
- Google maps sdk para la visualizacion del mapa