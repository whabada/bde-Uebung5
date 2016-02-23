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

Ein Blick in die Table zeigt, dass ingesamt 8024 Rows durchlaufen werden. 

``` 
hbase(main):002:0> scan 'Translations'
[Die folgenden Daten sind ein Auszug]
 zippers                                  column=German:zippers, timestamp=1456215681524, value=Reissverschlu.sse|            
 zither                                   column=German:zither, timestamp=1456215681524, value=Zither|                        
 zitherist                                column=German:zitherist, timestamp=1456215681524, value=Zitherspieler|              
 zloty                                    column=German:zloty, timestamp=1456215681524, value=Zloty (poln. Wa.hrung)[Noun]|   
 zlotys                                   column=German:zlotys, timestamp=1456215681524, value=Zlotys (poln. Wa.hrung)[Noun]| 
 zodiac                                   column=French:zodiac, timestamp=1456215681524, value=zodiaque[Noun]| zondiaque[Noun]|
 zodiac                                   column=German:zodiac, timestamp=1456215681524, value=Sternzeichen[Noun]| Tierkreis| tierkreis|                        
 zodiacal                                 column=German:zodiacal, timestamp=1456215681524, value=Tierkreis-|
 zombi                                    column=German:zombi, timestamp=1456215681524, value=Zombi[Noun]|                    
 zombie                                   column=German:zombie, timestamp=1456215681524, value=Trant\xC3\xBCte (f)| 
 zone                                     column=French:zone, timestamp=1456215681524, value=zone[Noun]| zone[Noun]|
 zone                                     column=German:zone, timestamp=1456215681524, value=Gebiet (n)| Zone (f)|            
 zones                                    column=German:zones, timestamp=1456215681524, value=Zonen[Noun]|                    
 zoo                                      column=French:zoo, timestamp=1456215681524, value=zoo[Noun]|                        
 zoo                                      column=German:zoo, timestamp=1456215681524, value=Zoo (m)|                          
 zoologic                                 column=German:zoologic, timestamp=1456215681524, value=zoologisch[Adjective]|       
 zoological                               column=German:zoological, timestamp=1456215681524, value=zoologisch|                
 zoologist                                column=German:zoologist, timestamp=1456215681524, value=Zoologe[Noun]|              
 zoologists                               column=German:zoologists, timestamp=1456215681524, value=Zoologen[Noun]|            
 zoology                                  column=French:zoology, timestamp=1456215681524, value=zoologie[Noun]|               
 zoology                                  column=German:zoology, timestamp=1456215681524, value=Zoologie[Noun]|               
 zoom                                     column=French:zoom, timestamp=1456215681524, value=aller en trombe[Verb]|           
 zoom                                     column=German:zoom, timestamp=1456215681524, value=zoomen[Verb]|                    
 zoos                                     column=German:zoos, timestamp=1456215681524, value=Zoos, Tierga.rten[Noun]|     
 zucchini                                 column=French:zucchini, timestamp=1456215681524, value=courgette[Noun]| 
 8054 row(s) in 13.3340 seconds

```

Sucht man nun ein Wort, beispielsweise Wolverine kann man dies auch über die HBase Shell tun: 
``` 
hbase(main):003:0> get "Translations", "wolverine"
COLUMN                                    CELL                                                                                 
 French:wolverine                         timestamp=1456215681300, value=le carcajou[Noun]| le glouton[Noun]|                 
 German:wolverine                         timestamp=1456215681300, value=Vielfrass[Noun]|                                     
2 row(s) in 0.0140 seconds
``` 

