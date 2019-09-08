# nagiosmc
Nagiosmc is a plugin that exports nagios performance data which can then be read by [nagios](https://www.nagios.com/) to create performance graphs. The plugin also acts as an API for you to **easily** create your own exports

# Custom exports
The plugin acts as a library for you to create your own exports. Simply add the jar file to your plugin's classpath. 
**All @Nagios annotated classes need to be placed into a package called 'nagios'**

## Exports
Exports are the schedulers that write the query values to the disk. An exporter can have multiple queries, which are defined in the constructor. All queries in an exporter should be related, as the chart lines will be overlayed in the nagios PNP graph. Exporters must be placed in the 'nagios.exports' package, be annotated with at.tewan.nagiosmc.Nagios and extend at.tewan.nagiosmc.NagiosExport. Export classes are typically prefixed with 'Export'
Example:
```java

package nagios.export;

import at.tewan.nagiosmc.*;

@Nagios
public class ExportCoolNumbers implements NagiosExport {
  
  public ExportCoolNumbers() {
    super("numbers", "Random Numbers", "")
  }
  
}

```

## Queries
Queries are the part of your code that gets the actual data. To create a query, create a new class in the 'nagios.queries' package. (Query classes are typically prefixed with 'Query'). The class needs to implement at.tewan.nagiosmc.NagiosQuery and must be annotated with at.tewan.nagiosmc.Nagios

Example:
```java
package nagios.queries;

import at.tewan.nagiosmc.*;

@Nagios
public class QueryExampleRandomNumber implements NagiosQuery {

  // Required overrides
  
  @Override
  public float getValue() { // Returns the actual value. In this example, we're going to return a random number (Of course this is completely pointless)
    return Math.random() * getMax();
  }
  
  @Override
  public float getMin() { // Returns the lowest number the value can get
    return 0;
  }
  
  @Override
  public float getMax() { // Returns the highest number the value can reach
    return 100
  }
  
  @Override
  public String getName() { // Defines the query name. This name is going to be used by exporters so DON'T have conflicting names!
    return "Random Number";
  }
  
  // Optional overrides
  
  @Override
  public void init() { // Gets run after the Query is created and set up. It is recommended to use this method rather than using a constructor.
  
  }
  
  @Override
  public float getWarning() { // Nagios will draw a yellow line at this number and can be configured to notify you via email if the value reaches this amount
    return 60;
  }
  
  @Override
  public float getCritical() { // Nagios will draw a red line at this number and can be configured to notify you via email if the value reaches this amount
    return 90;
  }
  
}

```
