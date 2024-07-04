package io.swagger.model;

import javax.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * Workstation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-15T17:26:22.532+02:00[Europe/Berlin]")


@Entity
@Table(name = "jnktkmz_workstation")
public class Workstation   {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  private Long id = null;

  @Column
  @JsonProperty("workstation")
  private String workstation = null;

  @Column
  @JsonProperty("employee")
  private String employee = null;

  @Column
  @JsonProperty("rootpwd")
  private String rootpwd = null;

  public Workstation id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * id autoincrementale
   * @return id
   **/
  @Schema(example = "10", description = "id autoincrementale")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Workstation workstation(String workstation) {
    this.workstation = workstation;
    return this;
  }

  /**
   * Get workstation
   * @return workstation
   **/
  @Schema(example = "471-06-4256", description = "")
  
    public String getWorkstation() {
    return workstation;
  }

  public void setWorkstation(String workstation) {
    this.workstation = workstation;
  }

  public Workstation employee(String employee) {
    this.employee = employee;
    return this;
  }

  /**
   * Get employee
   * @return employee
   **/
  @Schema(example = "rbraggintonir@newyorker.com", description = "")
  
    public String getEmployee() {
    return employee;
  }

  public void setEmployee(String employee) {
    this.employee = employee;
  }

  public Workstation rootpwd(String rootpwd) {
    this.rootpwd = rootpwd;
    return this;
  }

  /**
   * Get rootpwd
   * @return rootpwd
   **/
  @Schema(example = "1b4711e449f31792ad329b738ca8442d8eeb4f642ef66ef89b7e6cac00cfc36d", description = "")
  
    public String getRootpwd() {
    return rootpwd;
  }

  public void setRootpwd(String rootpwd) {
    this.rootpwd = rootpwd;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Workstation workstation = (Workstation) o;
    return Objects.equals(this.id, workstation.id) &&
        Objects.equals(this.workstation, workstation.workstation) &&
        Objects.equals(this.employee, workstation.employee) &&
        Objects.equals(this.rootpwd, workstation.rootpwd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, workstation, employee, rootpwd);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Workstation {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    workstation: ").append(toIndentedString(workstation)).append("\n");
    sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
    sb.append("    rootpwd: ").append(toIndentedString(rootpwd)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
