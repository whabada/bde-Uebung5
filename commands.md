Zunaechst schauen wir uns eine Liste der aktuell exisitierenden Tables in HBase an: 

``` 
hbase(main):012:0> list
TABLE                                                                           
analytics_demo                                                                  
document_demo                                                                   
imageData                                                                       
vmd                                                                             
4 row(s) in 0.0200 seconds

=> ["analytics_demo", "document_demo", "imageData", "vmd"]
``` 

Nach der Exportierung und Ausführung der ueb5.jar haben wir folgendes Bild: 

``` 
hbase(main):002:0> list
TABLE                                                                                                                                                 
Translations                                                                                                                                          
analytics_demo                                                                                                                                        
document_demo                                                                                                                                         
imageData                                                                                                                                             
vmd                                                                                                                                                   
5 row(s) in 0.0480 seconds

=> ["Translations", "analytics_demo", "document_demo", "imageData", "vmd"]
```

Ein Blick in die Table zeigt, dass 
