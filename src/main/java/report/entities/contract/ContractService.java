package report.entities.contract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ContractService {

    private Path contractDir;

    public ContractService() {
        this.contractDir = Paths.get("D:\\IdeaProjects\\Report\\lib\\contracts");
    }

    public List<ContractTIV> list() throws IOException {
        return Files.list(contractDir).map(file -> {
            return new ContractTIV(file.toFile());
        }).collect(Collectors.toList());
    }

    public List<ContractTIV> listBySiteName(String siteName) throws IOException {
        return Files.list(
                Paths.get(contractDir.toString(), siteName))
                .map(file -> {
                    return new ContractTIV(file.toFile());
                }).collect(Collectors.toList());
    }
}
