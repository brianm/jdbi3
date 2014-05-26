#!/usr/bin/env ruby

while line = gets
  if line =~ /^(\s+)public (.+?) throws SQLException/
    puts "#$1public #$2"
  elsif line =~ /^(\s+)return (.+)/
    puts "#$1try { return #$2 } catch (SqlException e) { throw new JDBIException(e); }"
  else    
    puts line
  end
end
