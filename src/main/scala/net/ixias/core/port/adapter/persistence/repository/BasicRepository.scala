/*
 * This file is part of the IxiaS services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package net.ixias
package core.port.adapter.persistence.repository

import com.typesafe.config.{ Config, ConfigFactory }
import core.domain.model.{ Identity, Entity }
import core.port.adapter.persistence.io.{ EntityIOAction, EntityIOActionContext }
import core.port.adapter.persistence.backend.BasicBackend

/**
 * The profile for persistence that
 * does not assume the existence driver for database abstract layer.
 */
trait BasicProfile extends Profile with BasicActionComponent {

  /** The back-end type required by this profile */
  type Backend <: BasicBackend
  /** The type of the context used for running repository Actions */
  type Context =  EntityIOActionContext

  /** The back-end implementation for this profile */
  val backend: Backend

  /** The API for using the utility methods with a single import statement.
    * This provides the repository's implicits, the Database connections,
    * and commonly types and objects. */
  trait API extends super.API
  val api: API = new API {}

}

trait BasicActionComponent extends ActionComponent {
  profile: BasicProfile =>
}

trait BasicRepository[K <: Identity[_], V <: Entity[K]]
    extends BasicProfile with EntityIOAction[K, V] {
  /** The identity type of entity */
  type Id      = K
  /** The entity type of managed by this profile */
  type Entity  = V
}

