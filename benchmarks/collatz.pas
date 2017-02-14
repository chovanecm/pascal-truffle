program collatzt;
var n : integer;
var counter, start_n : integer;
var i,j : integer;
begin

start_n := 3711;

for i := 30000 downto 0 do
begin
	for j:= 100 downto 0 do
	begin
		n:= start_n;


		counter := 0;

		while n <> 1 do
		begin
			if n div 2 * 2 = n then
				n := n div 2
			else
				n := n * 3 + 1;
			counter := counter + 1;
		end;
	end;		
end;
writeln('For n = ', start_n, ', it took ', counter, ' steps.');
end.


