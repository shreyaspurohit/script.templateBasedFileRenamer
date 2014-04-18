//args(0), I/P Template: #SHOW#DELIM#SEASON#DELIM#SHOWNUM#DELIM#EPISODE#DELIM#EPNUM#DELIM#HYPHEN#DELIM#OTHER
//args(1), O/P Template: #SHOW#DELIM%-#DELIM%S#SHOWNUM%E#EPNUM#DELIM#HYPHEN#DELIM#OTHER
//args(2), Delimiter In Template to real delimiter mapping: #DELIM=" "
//args(3), Directory to scan

val ipTmpl = args(0)
val opTmpl = args(1)
val dlTmpl = args(2).split("=")(0) 
val dlReal = args(2).split("=")(1)
val scanDir =  args(3)

println()
println()

println("Filename mapper")
println("===============")

println("I/P Template: " + ipTmpl)
println("O/P Template: " + opTmpl)
println("Delimiter in Template: " + dlTmpl)
println("Delimiter in file name: " + dlReal)
println("Scan Directory: " + scanDir)
println()
println()

new java.io.File(scanDir)
	.listFiles
	.foreach{f => 
		println("Processing file " + f.getName)
		val newFileName = process(f.getName)
		println("New name: " + newFileName)
		val fNew = new java.io.File(scanDir + "/" + newFileName)
		if(fNew.exists()) throw new java.io.IOException("File Exists");
		val success = f.renameTo(fNew);
		if (!success) {
			println("Failed to rename " + f.getName)
		}
		println()
	}


//process("Friends Season 03 Episode 01 - The One with the Princess Leia Fantasy.avi")

def process(fName:String):String = {
	val realFileNameTokens = fName.split(dlReal)
	val nameMap = scala.collection.mutable.Map[String, String]()
	
	nameMap(dlTmpl) = dlReal
	
	var maxIndexInTmpl = 0
	var lastValueInTmpl = ""
	ipTmpl.split(dlTmpl).zipWithIndex.foreach{case (value,index) =>		
		nameMap(value) = realFileNameTokens(index)
		maxIndexInTmpl = index;
		lastValueInTmpl = value;
	}
	
	for(i <- maxIndexInTmpl+1 to realFileNameTokens.length-1){
		nameMap(lastValueInTmpl) = nameMap(lastValueInTmpl) + dlReal + realFileNameTokens(i)
	}	
	
	var newFileName = ""
	opTmpl.split("#").zipWithIndex.foreach{case (value, index) =>		
		if(!value.isEmpty){
			if(value.contains("%")){
				val tmplPart = value.split("%")(0)
				val tmplRealPart = value.split("%")(1)
				newFileName += nameMap("#" + tmplPart) + tmplRealPart
			}else{
				newFileName += nameMap("#" + value)
			}
		}		
	}
	return newFileName
}	