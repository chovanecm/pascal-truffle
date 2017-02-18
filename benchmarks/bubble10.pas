program bubble;
var arr : array [1..10000] of integer;
var n, i, j : integer;
var tmp : integer;
var sorted: boolean;
var swaps : integer;
var runs : integer;
begin
{run the program 10 times}

writeln('Running the program 10 times...');

for runs := 1 to 10 do
begin

n := 10000;
swaps := 0;
{ generate array }

for i:= 1 to n do
begin
	arr[i] := n - i;
end;

{bubble sort}

for i:=2 to n do
begin
	for j:=n downto i do
	begin
		if arr[j] < arr[j-1] then
		begin
			tmp := arr[j];
			arr[j] := arr[j-1];
			arr[j-1] := tmp;
			swaps:=swaps+1;
		end;
	end;
end;

{ check if array is sorted }
sorted := true;

for i:= 1 to n-1 do
begin
	if arr[i] > arr[i+1] then
	begin
		sorted:=false;
	end;
end;

writeln('Array sorted: ', sorted);
writeln('Swaps needed: ', swaps);

{end for runs:=1}
end;

end.


