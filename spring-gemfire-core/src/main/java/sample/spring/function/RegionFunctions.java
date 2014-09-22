package sample.spring.function;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;

import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.util.StringUtils;

import sample.gemfire.cache.RegionNotFoundException;

/**
 * The RegionFunctions class is a POJO class containing GemFire Server Cache Functions for data Cache Regions.
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.function.annotation.GemfireFunction
 * @see com.gemstone.gemfire.cache.Region
 * @see com.gemstone.gemfire.cache.execute.FunctionContext
 * @see com.gemstone.gemfire.cache.execute.RegionFunctionContext
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class RegionFunctions {

  @GemfireFunction
  public String echo(final String message) {
    return String.format("You said '%1$s'!", message);
  }

  @GemfireFunction
  public Integer regionSize(final FunctionContext context, final String regionNamePath) {
    Region region = getRegion(context, regionNamePath);

    if (region != null) {
      return region.size();
    }

    throw new RegionNotFoundException("The Region on which the size will be determined was not found!");
  }

  protected Region getRegion(final FunctionContext context, final String regionNamePath) {
    Cache cache = CacheFactory.getAnyInstance();
    Region region = (context instanceof RegionFunctionContext ? ((RegionFunctionContext) context).getDataSet() : null);

    if (region == null && !StringUtils.isEmpty(regionNamePath)) {
      region = cache.getRegion(regionNamePath);
    }

    if (region == null && context.getArguments() instanceof String) {
      region = cache.getRegion(context.getArguments().toString());
    }

    return region;
  }

}
